
angular.module('openspecimen')
  .directive('osPvs', function(PvManager) {
    function linker(scope, element, attrs, formCtrl) {
      scope.pvs = [];
      scope.reload = true;
      scope.ngModelListener = null;

      if (attrs.parentVal) {
        scope.$watch(attrs.parentVal,
          function(newVal) {
            if (newVal == undefined) {
              return;
            }

            scope.reload = true;
            loadPvs(formCtrl, scope, null, attrs, newVal);
          }
        );
      } else if (!attrs.multiple) {
        scope.ngModelListener = scope.$watch(attrs.ngModel,
          function(newVal) {
            if (newVal == undefined) {
              return;
            }

            scope.reload = true;
            scope.searchPvs(newVal);
          } 
        );
      }

      scope.searchPvs = function(searchTerm) {
        if (scope.reload) {
          loadPvs(formCtrl, scope, searchTerm, attrs);
        }

        if (scope.ngModelListener) {
          scope.ngModelListener();
        }
      };
    }

    function loadPvs(formCtrl, scope, searchTerm, attrs, parentVal) {
      var q = undefined;
      if (attrs.parentVal) {
        var prop = 'P:' + attrs.attribute + ':' + parentVal;
        q = getCachedValues(formCtrl, 'pvs', prop, function() { return _loadPvsByParent(attrs, parentVal); });
      } else {
        if (!searchTerm) {
          var prop = attrs.attribute + ":" + attrs.showOnlyLeafValues;
          q = getCachedValues(formCtrl, 'pvs', prop, function() { return _loadPvs(scope, attrs, searchTerm); });
        } else {
          q = _loadPvs(scope, attrs, searchTerm);
        }
      }

      q.then(
        function(pvs) {
          setPvs(scope, searchTerm, attrs, pvs);
        }
      );
    }

    function getCachedValues(formCtrl, group, prop, getter) {
      if (!formCtrl) {
        return getter();
      }

      return formCtrl.getCachedValues(group, prop, getter);
    }

    function _loadPvs(scope, attrs, searchTerm) {
      return PvManager.loadPvs(attrs.attribute, searchTerm, undefined, attrs.showOnlyLeafValues, true);
    }

    function _loadPvsByParent(attrs, parentVal) {
      return PvManager.loadPvsByParent(attrs.attribute, parentVal, false, undefined, true);
    }

    function setPvs(scope, searchTerm, attrs, pvs) {
      scope.pvs = pvs;
      if (attrs.unique == 'true') {
        scope.pvs = pvs.filter(function(el, pos) { return pvs.indexOf(el) == pos; });
      }

      if (!searchTerm && pvs.length < 100) {
        scope.reload = false;
      }
    }
    
    return {
      restrict: 'E',
      require: '?^osFormValidator',
      scope: true,
      replace: true,
      link : linker,
      template: '<os-select refresh="searchPvs($select.search)" list="pvs" select-prop="value" display-prop="displayValue"></os-select>'
    };
  });
