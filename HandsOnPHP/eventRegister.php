<?php
  include("database.php");

  $userid = $_GET['userid'];
  $eventid = $_GET['eventid'];

  $qq = mysql_query("SELECT logged_in FROM user WHERE id = '$userid'", $con) or die(mysql_error());
  $r = mysql_fetch_row($qq);                                                                       
  echo $r[0]."<br>";
  if(strcmp($r[0],"1") == 0) {
    $q = mysql_query("SELECT max_attendance,status FROM events WHERE id = '$eventid'", $con) or die(mysql_error());
    $r = mysql_fetch_row($q);
    $max_attend = $r[0];
    $status = $r[1];
    $registered = false;
    $q = mysql_query("SELECT user_id FROM subscription WHERE event_id = '$eventid'", $con) or die(mysql_error());
    $attending = mysql_num_rows($q);
    echo $attending."<br>";
    while($r = mysql_fetch_row($q)) {
      if(strcmp($userid,$r[0]) == 0) { // user is already registered for this event...
        $registered = true;  //set flag
      }
    }
    
    if((($attending + 1 <= $max_attend) && !$registered) && strcmp($status,"Active") == 0) {       //we're within the limit and we aren't already registered.
      $q = mysql_query("INSERT INTO subscription (user_id, event_id) VALUES('$userid', '$eventid')", $con) or die(mysql_error());
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
?>