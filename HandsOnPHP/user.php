<?php

require("./db.php");

class User {
	protected $db;
	protected $username;
	protected $password;
	protected $column;
	
	function __construct($name, $pass) {
		$this->db = new Database();
		$this->db->connect();
		$this->username = $name;
		$this->password = $pass;
		$this->setType();
	}
	
	private function setType() {
		if(strpos($this->username, "@") > 0) {
			$this->column = "email";
		} else {
			$this->column = "username";
		}
	}
	
	public function login() {
		$result = $this->db->selectWhere("password", "users", $this->column, "=", "'" . $this->username . "'");
		$row = $result->fetch_assoc();
		if(strcmp($row['password'], $this->password) == 0 && !empty($this->column)) {
			$this->db->update("logged_in", "users", $this->column, "=", $this->username, 1);
			$result = array("logged_in" => "1");
			echo json_encode($result);
		} else {
			$result = array("logged_in" => "0", "error" => "There was an error logging you in! Please check your password and try again.");
			echo json_encode($result);
		}
	}
	
}

?>