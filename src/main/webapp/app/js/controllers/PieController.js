angular.module('myApp').controller('PieController', function ($scope, HomeService, $log, $http){

    $scope.greet = 'hello world';
    $scope.data = ['sdf'];


    init();
    function init(){

        $http({
            method: 'POST',
            url:'http://localhost:9200/jdbc/_search?pretty&q=*&size=5',
            body: '{query: {filtered: {query: {term:{productNum:90536}},filter: {range: {transDate: {from: 2013-09-26T12:46:00.000Z}}}}}}'
        })
                .success(function (data, status, headers, config) {
                    $scope.info = data;
                    console.log(data);
                    for(var i = 0; i< data.hits.hits.length; i++){
                        //console.log(data.hits.hits[i]._source.productNum);
                        $scope.chart.labels.push(data.hits.hits[i]._source.transDate);
                        $scope.data.push(data.hits.hits[i]._source.productNum);
                    }
                })
                .error(function (data, status, headers, config) {
                    console.log(data);
                });

    }

    $scope.pieData = [
           {value: 30, color: "#F38630"},
           {value: 50, color: "#E4C"},
           {value: 54, color: "#10E4C3"},
           {value: 20, color: "#80E4Ce"},
           {value: 10, color: "#69D2E7"}
         ];

     //var myPie = new Chart(document.getElementById("canvas").getContext("2d")).Pie(pieData);

    $scope.chart = {
        labels : [],
        datasets : [
            // {
            //   fillColor : "rgba(151,187,205,0)",
            //   strokeColor : "#e67e22",
            //   pointColor : "rgba(151,187,205,0)",
            //   pointStrokeColor : "#e67e22",
            //   data : [4, 3, 5, 4, 6]
            // },
            {
                fillColor : "rgba(151,187,205,0)",
                strokeColor : "#f1c40f",
                pointColor : "rgba(151,187,205,0)",
                pointStrokeColor : "#f1c40f",
                //data : []
                data : $scope.data
            }
        ]
    };
});







