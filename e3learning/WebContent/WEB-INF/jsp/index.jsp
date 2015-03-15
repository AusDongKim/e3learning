<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%
	String root = request.getContextPath();
%>
<!DOCTYPE html>
<html lang="eng" ng-app="myApp">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>e3Learning A&T Management System</title>

<!-- Bootstrap -->
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap.min.css">
<!-- Optional theme -->
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/css/bootstrap-theme.min.css">
<style type="text/css">
body

</style>
<!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
<!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
<!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->
</head>
<body>
	<div class="container">
		<div class="header" ng-controller="headerController">
			<h2>e3Learning A&T management System</h2>
			<p>e3Learning Account, Training Management System</p>
			<nav class="navbar navhar-default">
				<a href="#addAccount" class="btn btn-primary btn-lg active" role="button">Account Management</a>
				<a href="#addCourse" class="btn btn-primary btn-lg active" role="button">Course Management</a>
			</nav>
		</div>
		<div class="content">
			<div ng-view>
			
			</div>
		</div>
		
	</div>
	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="http://www.underscorejs.org/underscore.js"></script>
	<!-- Include all compiled plugins (below), or include individual files as needed -->
	<script src="https://code.jquery.com/jquery-1.11.2.js"></script>
	<script
		src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.2/js/bootstrap.min.js"></script>
	<!-- AngularJS -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular.js"></script>
	<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.3.14/angular-route.js"></script>
	
	<script>
		$().ready(function() {
		});

		var myApp = angular.module("myApp", ['ngRoute']).filter("viewActiveState",function(){
			return function(input) {
				if(input=="A") return "Active";
				else if(input=="S") return "Suspend";
				else if(input=="D") return "Defect";
				else return "";
			};
		}).filter("viewTrainingSate",function(){
			return function(input) {
				if(input=="I") return "In progress";
				else if(input=="F") return "Finished";
				else return "";
			};
		});
		
		myApp.config(['$routeProvider',function($routeProvider){
			$routeProvider
			.when('/addAccount',{
				templateUrl:'<%=root%>/resources/template/a.html'
			})
			.when('/addCourse',{
				templateUrl:'<%=root%>/resources/template/s.html'
			}).
			otherwise({
				redirectTo:'/addAccount'
			});
		}]);
		
		myApp.controller("headerController",['$scope','$log',function($scope,$log){
			
		}]);
		
		myApp.controller("accountController",['$http','$scope',function($http,$scope){
			var self = this;
			$scope.viewName="Account Managmenet";
			$scope.accountData = [];
			$scope.init = function(){
				$.ajax({ 
					  type: 'GET', 
					  url: "<%=root%>/accounts",
					  success:  function(data){
						    
						    if(data.transactionStatus==false) {
						    	alert(data.errorMsg.join(",").replace(/,/g,"\n"));						    	
						    	return false;						    	
						  	} else {
						  		self.initAccountData(data.body);
						  		
						  	}						  	
					  },
					  dataType: "json" 
				});
			};
			
			$scope.errorMsg = "";
			$scope.accountForm = {};
			
			$scope.registAccount=function(){				
				var res = $http.post('<%=root%>/account',angular.toJson($scope.accountForm));				
				res.success(function(data, status, headers, config) {
					if(data.transactionStatus==false) {			    							    	
				    	return false;						    	
				  	} else {
				  		$scope.accountForm = {};
				  		$scope.init();
				  	}
				});
				res.error(function(data, status, headers, config) {		
					//console.log(data.errorMsg.join(",").replace(/,/g,"\n"));
					alert("Failed, Retry:\n"+data.errorMsg.join(",").replace(/,/g,"\n"));
				});
			};
			
			
			$scope.viewAccountTrainingHistory=false;			
			$scope.accountTrainingHistory = [];
			$scope.addTrainingData= {};
			
			$scope.viewTraining = function(obj) {
				$scope.addTrainingData["account"] = obj;
				$scope.viewTrainingHistoryFlag=false;
				$.ajax({ 
					  type: 'GET', 
					  url: "<%=root%>/training/"+obj.idAccount,
					  success:  function(data){
						    if(data.transactionStatus==false) {
						    	alert(data.errorMsg.join(",").replace(/,/g,"\n"));						    	
						    	return false;						    	
						  	} else {
						  		$scope.viewAccountTrainingHistory=true;
						  		$scope.accountTrainingHistory=data.body;
						  		$scope.$digest();
						  		window.scrollTo(0, $("#trainingView").position().top+10);
						  	}						  	
					  },
					  dataType: "json" 
				});
			};
			
			self.initAccountData = function(body) {				
				$scope.accountData = body;
				$scope.$apply();
			};
			
			$scope.addAcount = function() {
				//TODO Inintail 
				$("#addAccountModal").modal('show');
			};
			$scope.courseData=[];
			$scope.addTraining = function(){				
				$.ajax({ 
					  type: 'GET', 
					  url: "<%=root%>/courses",
					  success:  function(data){
						    if(data.transactionStatus==false) {
						    	alert(data.errorMsg.join(",").replace(/,/g,"\n"));						    	
						    	return false;						    	
						  	} else {
						  		self.initCourseData(data.body);
						  	}						  	
					  },
					  dataType: "json" 
				});
				$("#addTrainingModal").modal('show');
			};
			
			$scope.registerTraining = function(course) {
				$scope.addTrainingData["course"] = course;
				var trainingObj = {startDate:"01042015",endDate:"",account:$scope.addTrainingData.account,
						course:$scope.addTrainingData.course};
				var jsonStr = angular.toJson(trainingObj);
				$.ajax({ 
					  type: 'POST', 
					  url: "<%=root%>/training", 
					  data: jsonStr,
					  contentType: "application/json",
					  success:  function(data){
						    if(data.transactionStatus==false) {
						    	alert(data.errorMsg.join(",").replace(/,/g,"\n"));						    	
						    	return false;						    	
						  	} else {
						  	}						  	
					  },
					  dataType: "json", 
					  async:false
				});	
				
			};
			
			self.initCourseData = function(body) {				
				$scope.courseData = body;
				$scope.$apply();
			};
			
			
			
		}]);
		myApp.controller("courseController",['$scope','$routeParams',function($scope,$routeParams){
			var self = this;
			$scope.viewName="Course Managmenet";			
			$scope.courseData = [];
			$scope.init = function(){
				$.ajax({ 
					  type: 'GET', 
					  url: "<%=root%>/courses",
					  success:  function(data){
						    if(data.transactionStatus==false) {
						    	alert(data.errorMsg.join(",").replace(/,/g,"\n"));						    	
						    	return false;						    	
						  	} else {
						  		self.initCourseData(data.body);
						  	}						  	
					  },
					  dataType: "json" 
				});
			};
			
			$scope.addCourse = function(){
				$("#addCourseModal").modal('show');	
			};
			
			$scope.courseForm ={};
			$scope.registerCourse = function(){
				var jsonStr = angular.toJson($scope.courseForm);
				console.log(jsonStr);
				$.ajax({ 
					  type: 'POST', 
					  url: "<%=root%>/course", 
					  data: jsonStr,
					  contentType: "application/json",
					  success:  function(data){
						    if(data.transactionStatus==false) {
						    	alert(data.errorMsg.join(",").replace(/,/g,"\n"));						    	
						  	} else {
						  		$scope.courseForm={};
						  		$scope.$digest();
						  	}						  	
					  },
					  dataType: "json", 
					  async:false
				});	
			};
			
			self.initCourseData = function(body) {				
				$scope.courseData = body;
				$scope.$apply();
			};
			
		}]);
	</script>

</body>
</html>