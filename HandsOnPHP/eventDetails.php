<?php

require("./db.php");

class Details {
	protected $db;
	
	function __construct() {
		$this->db = new Database();
		$this->db->connect();
	}
	
	public function eventDetails($value) {
		$result = $this->db->selectWhere("*", "events", "id", "=", $value);
		return $result;
	}
}

?>