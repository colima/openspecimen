angular.module('os.biospecimen.common.specimenprops', [])
  .factory('SpecimenPropsSvc', function($http, $q, ApiUrls) {
    var callQ = undefined;
    var specimenPropsMap = undefined;

    function initCall() {
      callQ = $http.get(ApiUrls.getBaseUrl() + '/specimen-type-props/');
      return callQ;
    }

    function initPropsMap() {
      return callQ.then(
        function(result) {
          var tempMap = {};
          angular.forEach(result.data, function(prop) {
            var clsProps = tempMap[prop.specimenClass];
            if (!clsProps) {
              tempMap[prop.specimenClass] = clsProps = {};
            }

            clsProps[prop.specimenType] = prop;
          });

          specimenPropsMap = tempMap;
          return specimenPropsMap;
        }
      )
    }

    function getPropsFromMap(cls, type) {
      var clsProps = specimenPropsMap[cls];
      if (!clsProps) {
        return undefined;
      }

      var typeProps = undefined;
      if (!!clsProps[type]) {
        typeProps = clsProps[type];
      }

      return typeProps;
    }

    function getProps(cls, type) {
      var d = $q.defer();

      if (specimenPropsMap) {
        var icon = getPropsFromMap(cls, type);
        d.resolve(icon);
        return d.promise;
      }

      if (!callQ) {
        initCall();
      }

      initPropsMap().then(
        function() {
          var unit = getPropsFromMap(cls, type);
          d.resolve(unit);
        }
      );

      return d.promise;
    }

    return {
      getProps: getProps
    }
  })
  .directive('osSpecimenIcon', function(SpecimenPropsSvc) {
    return {
      restrict: 'E',

      replace: true,

      scope: {
        specimenClass: '=',
        type: '='
      },

      link : function (scope, element, attrs) {
        scope.specimenProps = undefined;
        SpecimenPropsSvc.getProps(scope.specimenClass, scope.type).then(
          function(props) {
            scope.specimenProps = props;
          }
        )
      },

      template: '<span ng-switch on="!!specimenProps.props.icon" class="os-icon-wrapper">' +
                '  <span ng-switch-when="true" class="{{specimenProps.props.icon}}"></span>' +
                '  <span ng-switch-default="true" class="os-specimen-icon">{{specimenProps.props.abbreviation}}</span>' +
                '</span>'
    };
  })
  .directive('osSpecimenUnit', function(SpecimenPropsSvc) {
    return {
      restrict: 'E',

      template: '<span></span>',

      replace: true,

      scope: {
        specimenClass: '=',
        type: '='
      },

      link: function(scope, element, attrs) {
        scope.$watchGroup(['specimenClass', 'type'], function(newVals) {
          if (!scope.specimenClass) {
            return;
          }

          var measure = attrs.measure || 'quantity';
          SpecimenPropsSvc.getProps(scope.specimenClass, scope.type).then(
            function(specimenProps) {
              var props = specimenProps.props;
              if (measure == "quantity") {
                element.html(props.qtyHtmlDisplayCode || props.qtyUnit);
              } else {
                element.html(props.concHtmlDisplayCode || props.concUnit);
              }
            }
          );
        });
      }
    }
  })
  .directive('osSpmnMeasure', function() {
    //
    // A DOM transformation directive; therefore shares the same scope as
    // the parent element
    //
    return {
      restrict: 'E',

      replace: true,

      template:
        '<div> ' +
          '<input type="text" class="form-control" ' +
            'ng-model-options="{allowInvalid: \'true\'}" ' +
            'ng-pattern="/^[0-9]*(\\.[0-9]+)?(([e][+-]?)[0-9]+)?$/"> ' +
          '<div> ' +
            '<os-specimen-unit></os-specimen-unit>' +
          '</div> ' +
        '</div>',

      compile: function(tElem, tAttrs) {
        var inputEl = tElem.find('input');
        inputEl.attr('name',        tAttrs.name);
        inputEl.attr('ng-model',    tAttrs.quantity);
        inputEl.attr('placeholder', tAttrs.placeholder);

        if (tAttrs.ngRequired) {
          inputEl.attr('ng-required', tAttrs.ngRequired);
        } else if (tAttrs.required != undefined) {
          inputEl.attr('required', '');
        }

        if (tAttrs.onChange) {
          inputEl.attr('ng-change', tAttrs.onChange);
        }

        if (tAttrs.ensureRange) {
          inputEl.attr('os-ensure-range', tAttrs.ensureRange);
        }

        var unitEl = tElem.find('os-specimen-unit');
        unitEl.attr('specimen-class', tAttrs.specimen + '.specimenClass');
        unitEl.attr('type',           tAttrs.specimen + '.type');
        unitEl.attr('measure',        tAttrs.measure || 'quantity');

        if (tAttrs.mdInput != undefined) {
          tElem.addClass('os-input-addon-grp os-md-input');
          inputEl.next().addClass('os-input-addon-right os-md-input-addon');
        } else {
          tElem.addClass('input-group');
          inputEl.next().addClass('input-group-addon');
        }

        return function() { };
      }
    }
  })
  .directive("osSpmnMeasureVal", function() {
    return {
      restrict: 'E',

      scope: {
        value   : '=',
        specimen: '=',
        measure : '@'
      },

      replace: true,

      template:
        '<span class="value value-md" ng-switch="!!value || value == 0">' +
        '  <span ng-switch-when="true">' +
        '    {{value | osNumberInScientificNotation}} ' +
        '    <os-specimen-unit specimen-class="specimen.specimenClass" type="specimen.type"' +
        '      measure="{{measure || \'quantity\'}}">' +
        '    </os-specimen-unit>' +
        '  </span>' +
        '  <span ng-switch-when="false" translate="common.not_specified"></span>' +
        '</span>'
    }
  });
