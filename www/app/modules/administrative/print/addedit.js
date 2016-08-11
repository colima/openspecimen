angular.module('os.administrative.print-rules.addedit', ['os.administrative.models'])
  .controller('PrintRuleAddEditCtrl', function(
    $scope, $http, $state, printrule, type, PrintRule, CollectionProtocol, Site) {

    function init() {
      $scope.rule = printrule;
      loadSites();
      $scope.CPs = [];
      loadAllCPs();
      $scope.type = type;
    }

    function loadSites() {
      Site.list().then(
        function(sites) {
          $scope.Sites = sites;
        }
      );
    }

    function loadAllCPs() {
      CollectionProtocol.query().then(
        function(cps) {
          $scope.CPs = cps.map(function(cp) { return cp.shortTitle; });
        }
      );
    };

    $scope.saveRule = function() {
      if(type == 'specimen' || $scope.rule.type=='specimen'){
        saveSpecimenPrintRule();
      }
      else
        saveVisitPrintRule();
    };

    function saveVisitPrintRule() {
      var printrule = angular.copy($scope.rule);
      delete printrule.type;

      if(printrule.id) {
        $http.put(PrintRule.url() + 'visit/' + printrule.id, printrule).then(
          function() {
            $state.go('print-rule-detail.overview', {ruleId: printrule.id,  type: type});
          }
        );
      }
      else {
        $http.post(PrintRule.url() + 'visit', printrule).then(
          function(savedRule) {
            $state.go('print-rule-detail.overview', {ruleId: savedRule.data.id,  type: 'visit'});
          }
        );
      }
    }

    function saveSpecimenPrintRule() {
      var specimenPrintRule = angular.copy($scope.rule);
      delete specimenPrintRule.type;

      if(specimenPrintRule.id) {
        $http.put(PrintRule.url() + 'specimen/' + specimenPrintRule.id, specimenPrintRule).then(
          function() {
            $state.go('print-rule-detail.overview', {ruleId: specimenPrintRule.id,  type: type});
          }
        );
      }
      else {
        $http.post(PrintRule.url() + 'specimen', specimenPrintRule).then(
          function(savedRule) {
            $state.go('print-rule-detail.overview', {ruleId: savedRule.data.id, type: 'specimen'});
          }
        );
      }
    };

    init();
  });
