angular.module('os.biospecimen.participant.newreg', ['os.biospecimen.models'])
  .controller('RegisterNewCtrl', function(
    $scope, $state, cpr, 
    Participant, CollectionProtocol, CollectionProtocolRegistration) {
    var registeredCps = [];

    function init() {
      $scope.cpr = cpr;
      $scope.newCpr = new CollectionProtocolRegistration({registrationDate: new Date()});

      initRegisteredCps();
    }

    function initRegisteredCps() {
      angular.forEach(cpr.participant.registeredCps, function(cp) {
        registeredCps.push(cp.cpShortTitle);
      });
    }

    $scope.loadCps = function(searchTitle) {
      $scope.cpList = [];
      CollectionProtocol.listForRegistrations($scope.cpr.getMrnSites(), searchTitle).then(
        function(cps) {
          for (var i = 0; i < cps.length; ++i) {
            if (registeredCps.indexOf(cps[i].shortTitle) == -1) {
              $scope.cpList.push(cps[i]);
            }
          }
        }
      );
    }

    $scope.register = function() {
      var cprToSave = new CollectionProtocolRegistration({
        participant: new Participant({id: cpr.participant.id, pmis: cpr.participant.getPmis()}),
        registrationDate: $scope.newCpr.registrationDate,
        cpId: $scope.newCpr.cp.id,
        ppid: $scope.newCpr.ppid
      });

      cprToSave.$saveOrUpdate().then(
        function(savedCpr) {
          $state.go('participant-detail.overview', {cprId: savedCpr.id});
        }
      );
    }

    init(); 
  });
