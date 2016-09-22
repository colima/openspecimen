
angular.module('os.biospecimen.participant.addedit', ['os.biospecimen.models', 'os.administrative.models'])
  .controller('ParticipantAddEditCtrl', function(
    $scope, $state, $stateParams, $translate, $modal, cp, cpr, extensionCtxt, hasDict, patientRegistration,
    CollectionProtocolRegistration, Participant,
    Site, PvManager, ExtensionsUtil) {

    var availableSites = [];
    var lookupParticipant = null;

    function init() {
      $scope.cpId = $stateParams.cpId;
      $scope.twoStepProcess = !cpr.id && patientRegistration.value == 'two';
      $scope.pid = undefined;
      $scope.allowIgnoreMatches = true;

      $scope.cp = cp;
      $scope.cpr = angular.copy(cpr);

      $scope.partCtx = {
        obj: {cpr: $scope.cpr}, inObjs: ['cpr']
      }

      $scope.deFormCtrl = {};
      $scope.extnOpts = ExtensionsUtil.getExtnOpts($scope.cpr.participant, extensionCtxt); 

      if (!hasDict) {
        $scope.cpr.participant.addPmi($scope.cpr.participant.newPmi());
        loadPvs();
      }
    };

    function loadPvs() {
      var op = !!$scope.cpr.id ? 'Update' : 'Create';

      $scope.sites = [];
      Site.listForParticipants(op, true).then(function(sites) {
        availableSites = sites;
        filterAvailableSites();
      });

      $scope.genders = PvManager.getPvs('gender');
      $scope.vitalStatuses = PvManager.getPvs('vital-status');
    };

    function filterAvailableSites() {
      var siteNames = $scope.cpr.getMrnSites();
      $scope.sites = availableSites.filter(
        function(site) {
          return siteNames.indexOf(site) == -1;
        }
      );
    }

    function registerParticipant() {
      var formCtrl = $scope.deFormCtrl.ctrl;
      if (formCtrl && !formCtrl.validate()) {
        return;
      }

      var cprToSave = angular.copy($scope.cpr);
      cprToSave.cpId = $scope.cpId;

      if (formCtrl) {
        cprToSave.participant.extensionDetail = formCtrl.getFormData();
      }

      cprToSave.$saveOrUpdate().then(
        function(savedCpr) {
          if (savedCpr.activityStatus == 'Active') {
            var registeredCps = cpr.participant.registeredCps;
            angular.extend(cpr, savedCpr);
            cpr.participant.registeredCps = registeredCps;
            $state.go('participant-detail.overview', {cprId: savedCpr.id});
          } else {
            $state.go('participant-list', {cpId: $scope.cp.id});
          }
        }
      );
    };

    $scope.pmiText = function(pmi) {
      return pmi.siteName + (pmi.mrn ? " (" + pmi.mrn + ")" : "");
    }

    $scope.addPmiIfLast = function(idx) {
      var participant = $scope.cpr.participant;
      if (idx == participant.pmis.length - 1) {
        participant.addPmi(participant.newPmi());
      }
    };

    $scope.removePmi = function(pmi) {
      var participant = $scope.cpr.participant;
      participant.removePmi(pmi);
      filterAvailableSites();    

      if (participant.pmis.length == 0) {
        participant.addPmi(participant.newPmi());
      }
    };

    $scope.onSiteSelect = filterAvailableSites;

    $scope.register = function() {
      var participant = $scope.cpr.participant;
      if (!participant.isMatchingInfoPresent()) {
        registerParticipant();
      } else {
        participant.getMatchingParticipants().then(
          function(result) {
            if (!result || result.length == 0) {
              registerParticipant();
            }

            $scope.allowIgnoreMatches = true;
            for (var i = 0; i < result.length; ++i) {
              var matchedAttrs = result[i].matchedAttrs;
              if (matchedAttrs.length > 1 || (matchedAttrs[0] != 'lnameAndDob')) {
                $scope.allowIgnoreMatches = false;
                break;
              }
            } 

            $scope.allowIgnoreMatches = participant.id || $scope.allowIgnoreMatches;
            $scope.matchedParticipants = result;
            lookupParticipant = $scope.cpr.participant; 
          }
        );
      }
    };

    $scope.matchedAttrText = function(attr) {
      return $translate.instant("participant.matching_attr." + attr);
    }

    $scope.selectParticipant = function(participant) {
      $scope.selectedParticipant = participant;
      $scope.cpr.participant = participant;
    };

    $scope.lookupAgain = function() {
      $scope.cpr.participant = lookupParticipant;
      $scope.matchedParticipants = undefined;
      $scope.selectedParticipant = undefined;
      $scope.allowIgnoreMatches = true;
    };

    $scope.ignoreMatchesAndRegister = function() {
      $scope.cpr.participant = lookupParticipant;
      registerParticipant();
    };

    $scope.registerUsingSelectedParticipant = function() {
      $scope.cpr.participant = new Participant($scope.selectedParticipant);
      registerParticipant();
    };

    $scope.confirmMerge = function() {
      var modalInstance = $modal.open({
        templateUrl: "modules/biospecimen/participant/confirm-merge.html",
        controller: function($scope, $modalInstance) {
          $scope.ok = function() {
            $modalInstance.close(true);
          }

          $scope.cancel = function() {
            $modalInstance.dismiss('cancel');
          }
        }
      });

      modalInstance.result.then(
        function() {
          $scope.registerUsingSelectedParticipant(); 
        }
      );
    }

    $scope.lookup = function() {
      var participant = $scope.cpr.participant;
      participant.getMatchingParticipants().then(
        function(result) {
          if (!result || result.length == 0) {
            $scope.twoStepProcess = false;
            $scope.isLookupFailed = true;
          } else {
            $scope.allowIgnoreMatches = false;
            $scope.matchedParticipants = result;

            lookupParticipant = $scope.cpr.participant; 
            $scope.selectParticipant(result[0].participant);
          }
        }
      );
    }

    init();
  });
