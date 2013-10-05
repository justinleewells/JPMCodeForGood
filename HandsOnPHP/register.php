<?php
    include("database.php");
    //VALUES...
    //dob (day,month,year), address (street, city, state, country), phone


    function validateName($name) {
  		if (!preg_match('/^[A-Za-z\\-\']+$/',$name)) {
  			return false;
  		} else {
  			return true;
  		}
  	}
  	
  	function generateRandomString($length = 10) {
        return substr(str_shuffle("0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ"), 0, $length);
    }
    
    $email=$_GET['email'];
    $password = $_GET['password'];

	  $first=$_GET['first'];
    $last=$_GET['last'];
    $phone=$_GET['phone'];
    
    $username=$_GET['username'];
    
    $address=$_GET['address'];
    $city=$_GET['city'];
    $state=$_GET['state'];
    $country=$_GET['country'];
    $zip=$_GET['zip'];
    
    $month=$_GET['month'];
    $day=$_GET['day'];
    $year=$_GET['year'];
		
	$query = mysql_query("SELECT 'id' FROM user WHERE email='$email' OR username='$username'", $con);
	$numrows = mysql_num_rows($query);
	if ($numrows==0) {
		$id = generateRandomString(10);
		$sql = "INSERT INTO user (id, first_name, last_name, username, email, address, city, state, country, password, dob_month, dob_day, dob_year, logged_in) 
		VALUES ('$id', '$first', '$last', '$username', '$email', '$address', '$city', '$state', '$country', '$password', $month, $day, $year, 0);";
		//echo $sql;
		mysql_query($sql,$con) or die('Error: ' . mysql_error());
		
		$result = array("success"=>"Thanks, ".$first.", for registering!");
		echo json_encode($result);
	} else {
		$result = array("error"=>"Sorry, it looks like you've already registered with us. Have you forgotten your password?");
		echo json_encode($result);
	}


?>