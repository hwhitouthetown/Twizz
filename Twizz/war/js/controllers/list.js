(function(){
    angular
        .module("tweetsQuiz")
        .controller("listCtrl", ListController);

    ListController.$inject = ['quizMetrics', 'DataService'];

    function ListController(quizMetrics, DataService){
        var vm = this;

        vm.quizMetrics = quizMetrics;
        vm.activeTweet = {};
        vm.changeActiveTweet = changeActiveTweet;
        vm.activateQuiz = activateQuiz;
        vm.search = "";

        function changeActiveTweet(index){
            vm.activeTweet = index;
        }

        function activateQuiz(){
            quizMetrics.changeState("quiz", true);
        }
    }
})();
