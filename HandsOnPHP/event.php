<?php

require_once("./database.php");

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
		//echo $r[0]."<br>";
		if(strcmp($r['logged_in'],"1") == 0) {
			/*$q = mysql_query("SELECT max_attendance,status FROM events WHERE id = '$eventid'", $con) or die(mysql_error());
			$r = mysql_fetch_row($q);*/
			$result = $this->db->selectWhere("max_attendance, status", "events", "id", "=", "'$eventid'");
			$r = $result->fetch_assoc();
			
			$max_attend = $r['max_attendance'];
			$status = $r['status'];
			$registered = false;
			
			$re = $this->db->selectWhere("attending", "events", "id", "=", "'$eventid'");
			$p = $re->fetch_assoc();
			$attending = $p['attending'];
			//echo "<br><br>".$attending."<br><br>";
			
			//echo $attending."<br>";
			while($r = $result->fetch_assoc()) {
				if(strcmp($userid,$r['user_id']) == 0) { // user is already registered for this event...
					$registered = true;  //set flag
				}
			}
			
			if((($attending + 1 <= $max_attend) && !$registered) && strcmp($status,"Active") == 0) {       //we're within the limit and we aren't already registered.
				//$q = mysql_query("INSERT INTO subscription (user_id, event_id) VALUES('$userid', '$eventid')", $con) or die(mysql_error());
				$attending++;
				$this->db->update("attending", "events", "id", "=", "'$eventid'", $attending);
				$this->db->insert("subscription", "(user_id, event_id)", "('$userid','$eventid')");
				$result = array("msg"=>"You have been registered!");
				echo json_encode($result);
			} else { //something went wrong, let's pass back some error info...
				if($registered) $result = array("msg"=>"You have already registered for this event.");
				if(!$registered) $result = array("msg"=>"This event is full.");
				if(strcmp($status,"Active") <> 0) $result = array("error"=>"This event is not active.");
				echo json_encode($result);
			}
		} else {
			$result = array("msg"=>"You are not logged in! Please log in to be able to register for events!");
		}
		return $result;
	}
	
	function unsubscribe($eventid, $userid) {
		$result = $this->db->selectWhere("logged_in", "user", "id", "=", "'$userid'");
		$r = $result->fetch_assoc();                                                                       
		//echo $r['logged_in']."<br>";
		if(strcmp($r['logged_in'],"1") == 0) {
		  $re = $this->db->selectWhere("attending", "events", "id", "=", "'$eventid'");
			$p = $re->fetch_assoc();
			$attending = $p['attending'];
			$attending --;
			if($attending>=0) $this->db->update("attending", "events", "id", "=", "'$eventid'", $attending);
			$result = $this->db->delete("subscription", "user_id", "=", "'$userid' AND event_id='$eventid'");
			$ret = array("msg"=>"You have been removed from this event's attendance.");
		} else {
			$ret = array("msg"=>"Sorry, something bad happened when trying to remove you from this event's attendance. Please try again.");
		}
		return $ret;
	}

}
?>
