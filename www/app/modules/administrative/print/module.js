
angular.module('os.administrative.print-rules',
  [
   'os.administrative.print-rules.list',
   'os.administrative.print-rule.detail',
   'os.administrative.print-rules.addedit'
  ])

  .config(function($stateProvider) {
    $stateProvider
      .state('print-root', {
        abstract: true,
        template: '<div ui-view></div>',
        parent: 'signed-in'
      })
      .state('print-list', {
        url: '/print',
        templateUrl: 'modules/administrative/print/list.html',
        controller: 'PrintRulesListCtrl',
        parent: 'print-root'
      })
      .state('print-rule-detail', {
        url: '/print/:ruleId/:type',
        templateUrl: 'modules/administrative/print/detail.html',
        resolve: {
          printrule: function($stateParams, PrintRule) {
            if ($stateParams.ruleId && $stateParams.type == 'visit') {
              return PrintRule.getById($stateParams.ruleId);
            }

            if ($stateParams.ruleId && $stateParams.type == 'specimen') {
              return PrintRule.getSpecimenById($stateParams.ruleId).then(
                function(result) {
                  return result.data;
                }
              );
            }
            return new PrintRule();
          },
          type: function($stateParams) {
            return $stateParams.type;
          }
        },
        controller: 'PrintRuleDetailCtrl',
        parent: 'print-root'
      })
      .state('print-rule-detail.overview', {
        url: '/overview',
        templateUrl: 'modules/administrative/print/overview.html',
        parent: 'print-rule-detail'
      })
      .state('print-rule-addedit', {
        url: '/print-addedit/:ruleId/:type',
        templateUrl: 'modules/administrative/print/addedit.html',
        resolve: {
          printrule: function($stateParams, PrintRule) {
            if ($stateParams.ruleId && $stateParams.type == 'visit') {
              return PrintRule.getById($stateParams.ruleId);
            }
            if($stateParams.ruleId && $stateParams.type == 'specimen') {
              return PrintRule.getSpecimenById($stateParams.ruleId).then(
                function(result) {
                  return result.data;
                }
              );
            }
            return new PrintRule();
          },
          type: function($stateParams) {
            return $stateParams.type;
          }
        },
        controller: 'PrintRuleAddEditCtrl',
        parent: 'print-root'
      })
  });