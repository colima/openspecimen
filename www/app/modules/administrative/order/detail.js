angular.module('os.administrative.order.detail', ['os.administrative.models'])
  .controller('OrderDetailCtrl', function($scope, $state, order, Util, SettingUtil, DeleteUtil) {
  
    function init() {
      $scope.order = order;
      $scope.rptTmplConfigured = false;
      if (!!order.distributionProtocol.report && !!order.distributionProtocol.report.id) {
        $scope.rptTmplConfigured = true;
      } else {
        SettingUtil.getSetting('common', 'distribution_report_query').then(
          function(setting) {
            $scope.rptTmplConfigured = !!setting.value;
          }
        );
      }
    }

    $scope.downloadReport = function() {
      Util.downloadReport(order, 'orders');
    };

    $scope.deleteOrder = function() {
      DeleteUtil.confirmDelete({
        entity: {name: $scope.order.name},
        templateUrl: 'modules/administrative/order/delete.html',
        delete: function() {
          $scope.order.$remove().then(
            function(deletedOrder) {
              $state.go('order-list');
            }
          );
        }
      });
    };

    init();
  });
