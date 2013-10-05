<?php

require_once("./database.php");

class Search {
	protected $db;
	
	function __construct() {
		$this->db = new Database();
		$this->db->connect();
	}
	
	public function zipcode($value) {
		$result = $this->db->selectWhere("*", "events", "zip_postal_code", "=", $value);
		return $result;
	}
	
	public function keyword($value) {
		$result = $this->db->selectLike("*", "events", "description", "'%" . $value . "%'");
		return $result;
	}
	
	public function impactArea($value) {
		$result = $db->selectWhere("*", "events", "primary_impact_area", "=", $value);
		return $result;
	}
	
	public function startDate($value) {
		$result = $db->selectWhere("*", "events", "start_date", "=", $value);
		return result;
	}
}

?>