<?php
 
  require("./db.php");
  
  class Interest {
  	protected $db;
  	
  	function __construct() {
  		
  	}
  	
  	public function eventInterest($value) {
      return array("success"=>"Thanks for showing your interest! An email has been sent to the event coordinator expressing your interest.");
  	}
  }

?>