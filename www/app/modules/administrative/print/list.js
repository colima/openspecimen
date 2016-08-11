angular.module('os.administrative.print-rules.list', ['os.administrative.models'])
  .controller('PrintRulesListCtrl', function($scope, $state, PrintRule) {

    function init() {
      loadRules();
      loadSpecimenRules();
    }

    function loadRules() {
      PrintRule.query(true).then(
        function(ruleList) {
          $scope.printRules = ruleList;
        }
      );
    }

    function loadSpecimenRules() {
      PrintRule.getSpecimenPrintRule().then(
        function(ruleList) {
          $scope.specimenRules = ruleList.data;
        }
      );
    }

    $scope.showPrintRuleOverview = function(rule) {
      $state.go('print-rule-detail.overview', {ruleId:rule.id, type : 'visit'});
    };

    $scope.showSpecimenRuleOverview = function(rule) {
      $state.go('print-rule-detail.overview', {ruleId: rule.id, type : 'specimen'})
    }

    init();
  });
