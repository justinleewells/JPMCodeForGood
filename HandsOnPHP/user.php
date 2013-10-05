<?php

require("./db.php");

class User {
	protected $db;
	protected $username;
	protected $password;
	protected $email;
	protected $first_name;
	protected $last_name;
	protected $phone;
	protected $address;
	protected $dob;
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
	
	private function checkUsername($username) {
		$result = $this->db->selectWhere("*", "user", "username", "=", "'" . $username . "'");
		if (!$result) {
			return true;
		} else {
			return false;
		}
	}
	
	private function checkEmail($email) {
		$result = $this->db->selectWhere("*", "user", "email", "=", "'" . $email . "'");
		if (!$result) {
			return true;
		} else {
			return false;
		}
	}
	
	private function generateRandomString($length = 15) {
		return substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $length);
  }
	
	public function setCredentials($data) {
		$this->username = $data['username'];
		$this->password = $data['password'];
		$this->email = $data['email'];
		$this->first_name = $data['first_name'];
		$this->last_name = $data['last_name'];
		$this->phone = $data['phone'];
		$this->address = $data['address'];
		$this->dob = $data['dob'];
		$this->setType();
	}
	
	public function login() {
		$result = $this->db->selectWhere("password", "user", $this->column, "=", "'" . $this->username . "'");
		$row = $result->fetch_assoc();
		if(strcmp($row['password'], $this->password) == 0 && !empty($this->column)) {
			$this->db->update("logged_in", "user", $this->column, "=", $this->username, 1);
			$result = array("logged_in" => "1");
		} else {
			$result = array("logged_in" => "0", "error" => "There was an error logging you in! Please check your password and try again.");
		}
		return $result;
	}
	
	public function logout() {
		$this->db->update("logged_in", "user", $this->column, "=", $this->username, 0);
		$result = array("logged_in"=>"0");
		return $result;
	}
	
	public function register() {
		if ($this->checkUsername($this->username) && $this->checkEmail($this->email])) {
			$id = $this->generateRandomstring(15);
			$result = $this->db->insert("user", "(id, first_name, last_name, username, email, address, password, dob, logged_in)",
												"('" . $id . "','" . $this->first_name . "','" . $this->last_name . "','" . $this->username . "','" . $this->email . "','" . $this->address .
												"','" . $this->password . "','" . $this->dob . "',0)");
			return $result;
		}
	}
	
}

?>