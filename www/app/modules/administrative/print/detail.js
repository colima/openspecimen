
angular.module('os.administrative.print-rule.detail', ['os.administrative.models'])
  .controller('PrintRuleDetailCtrl', function( $scope, $state, $modal, printrule, type, PrintRule, DeleteUtil) {

    function init() {
      $scope.printrule = printrule;
    }

    $scope.type = type;

    $scope.printRuleDelete = function() {
      DeleteUtil.confirmDelete({
        templateUrl: 'modules/administrative/print/delete.html',
        delete: function() {
          PrintRule.deleteRule(printrule.id, type).then(
            function() {
              $state.go('print-list');
            }
          );
        }
      });
    }

    init();
  });
