<?php
  require("./db.php");

  class Event {
    protected $db;
    
    function __construct() {
      $this->db = new Database();
		  $this->db->connect();
    }
  
    function getDetails($value) {
      $result = $this->db->selectWhere("*", "events", "id", "=", "'$value'");
		  return $result;
    }
    
    function subscribe($eventid, $userid) {
      $result = $this->db->selectWhere("logged_in", "user", "id", "=", "'$userid'");
      $r = $result->fetch_assoc();                                                                       
      echo $r[0]."<br>";
      if(strcmp($r[0],"1") == 0) {
        /*$q = mysql_query("SELECT max_attendance,status FROM events WHERE id = '$eventid'", $con) or die(mysql_error());
        $r = mysql_fetch_row($q);*/
        $result = $this->db->selectWhere("(max_attendance, status)", "events", "id", "=", "'$eventid'");
        $r = $result->fetch_assoc();
        
        $max_attend = $r[0];
        $status = $r[1];
        $registered = false;
         
        /*$q = mysql_query("SELECT user_id FROM subscription WHERE event_id = '$eventid'", $con) or die(mysql_error());
        $attending = mysql_num_rows($q);*/
        $result = $this->db->selectWhere("user_id", "subscription", "event_id", "=", "'$eventid'");
        $attending = $result->num_rows();
        
        echo $attending."<br>";
        while($r = $result->fetch_assoc()) {
          if(strcmp($userid,$r[0]) == 0) { // user is already registered for this event...
            $registered = true;  //set flag
          }
        }
        
        if((($attending + 1 <= $max_attend) && !$registered) && strcmp($status,"Active") == 0) {       //we're within the limit and we aren't already registered.
          //$q = mysql_query("INSERT INTO subscription (user_id, event_id) VALUES('$userid', '$eventid')", $con) or die(mysql_error());
          $this->db->insert("(user_id, event_id)", "subscription", "('$userid','$eventid')");
          $result = array("success"=>"You have been registered!");
          echo json_encode($result);
        } else { //something went wrong, let's pass back some error info...
          if($registered) $result = array("error"=>"You have already registered for this event.");
          if(!$registered) $result = array("error"=>"This event is full.");
          if(strcmp($status,"Active") <> 0) $result = array("error"=>"This event is not active.");
          echo json_encode($result);
        }
      } else {
        $result = array("error"=>"You are not logged in! Please log in to be able to register for events!");
        echo json_encode($result);
      }
    }
    
    function unsubscribe($eventid, $userid) {
    
    }
  
  }
?>
