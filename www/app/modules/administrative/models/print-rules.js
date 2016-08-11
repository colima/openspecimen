angular.module('os.administrative.models.print-rules', ['os.common.models'])
  .factory('PrintRule', function($http, $state, osModel) {
    var PrintRule = osModel('print-rules');

    PrintRule.getSpecimenPrintRule = function () {
      return $http.get(PrintRule.url() + '/' + 'specimen');
    }

    PrintRule.getSpecimenById = function (ruleId) {
      return $http.get(PrintRule.url() + 'specimen/' + ruleId);
    }

    PrintRule.deleteRule = function(ruleId, type) {
      if(type == 'specimen') {
        return $http.delete(PrintRule.url() + 'specimen/' + ruleId).then();
      }

      if(type == 'visit') {
        return $http.delete(PrintRule.url() + 'visit/' + ruleId).then();
      }
    }

    return PrintRule;
  });

