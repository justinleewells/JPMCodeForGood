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
    
    /*if(!validateName($_GET['first'])) {
      $errors++;
      $errormessage.="'First Name' field contains invalid characters! (\"A-Z,a-z,-,'\" are all allowed)\n";
    }
    if(!validateName($_GET['last'])) {
      $errors++;
      $errormessage.="'Last Name' field contains invalid characters! (\"A-Z,a-z,-,'\" are all allowed)\n";
    } */
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
    
    
    $errors=0;
    /*if((((((((((((empty($username) || empty($email)) || empty($password)) || empty($first)) || empty($last)) || empty($address)) || empty($city)) || empty($state)) || empty($country)) || empty($zip)) || empty($month)) || empty($day)) || empty($year)) {
      $errors++;
      $errormessage.="One or more of the input fields that required an entry was left blank. Please try again.\n";
    } */
    /*if(filter_var($email, FILTER_VALIDATE_EMAIL)) {
      $errors++;
      $errormessage.="'Email' field contains invalid characters or is an invalid address!\n";
    }
    if(strlen($password) < 6) {
      $errors++;
      $errormessage.="'Password' field must be at least 6 characters long!\n";
    }*/
    if($errors>0) {
      $result = array("error"=>$errormessage);
      echo json_encode($result); 
    } else if($errors==0) {
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
    }


?>