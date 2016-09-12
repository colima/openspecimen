angular.module('os.biospecimen.common.specimenicon', [])
  .factory('SpecimenIconSvc', function($http, $q, ApiUrls) {
    var callQ = undefined;
    var iconsMap = undefined;

    function initCall() {
      callQ = $http.get(ApiUrls.getBaseUrl() + '/specimen-props/icons');
      return callQ;
    }

    function initIconsMap() {
      return callQ.then(
        function(result) {
          var tempMap = {};
          angular.forEach(result.data, function(icon) {
            var clsIcons = tempMap[icon.specimenClass];
            if (!clsIcons) {
              tempMap[icon.specimenClass] = clsIcons = {};
            }

            clsIcons[icon.type] = icon;
          });

          iconsMap = tempMap;
          return iconsMap;
        }
      )
    }

    function getIconFromMap(cls, type) {
      var clsIcons = iconsMap[cls];
      if (!clsIcons) {
        return undefined;
      }

      var typeIcon = undefined;
      if (!!clsIcons[type]) {
        typeIcon = clsIcons[type];
      }

      return typeIcon;
    }

    function getIcon(cls, type) {
      var d = $q.defer();

      if (iconsMap) {
        var icon = getIconFromMap(cls, type);
        d.resolve(icon);
        return d.promise;
      }

      if (!callQ) {
        initCall();
      }

      initIconsMap().then(
        function() {
          var unit = getIconFromMap(cls, type);
          d.resolve(unit);
        }
      );

      return d.promise;
    }

    return {
      getIcon: getIcon
    }
  })
  .directive('osSpecimenIcon', function(SpecimenIconSvc) {
    return {
      restrict: 'E',

      replace: true,

      scope: {
        specimenClass: '=',
        type: '='
      },

      link : function (scope, element, attrs) {
        scope.icon = undefined;
        element.parent().removeAttr('title');
        element.parent().addClass('os-transparent');

        SpecimenIconSvc.getIcon(scope.specimenClass, scope.type).then(
          function(icon) {
            scope.icon = icon;
          }
        )
      },

      template: '<span ng-switch on="!!icon.iconClass" class="os-icon-wrapper">' +
                '  <span ng-switch-when="true" class="{{icon.iconClass}}"></span>' +
                '  <span ng-switch-default="true" class="os-specimen-icon">{{icon.abbreviation}}</span>' +
                '</span>'
    };
  });
