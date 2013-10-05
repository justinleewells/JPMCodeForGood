<?php
  include("database.php");
  
  $field = $_GET['identity'];
  $passw = $_GET['password'];
  //echo $field."<br>".$passw."<br>";
  
  if(strpos($field,"@") > 0) {
    //echo "IS AN EMAIL<br>"; 
    $column = "email";
  }
  else {
    //echo "IS NOT AN EMAIL...<br>";
    $column = "username";
  }
  
  $q = mysql_query("SELECT password FROM user WHERE ".$column." = '$field'", $con) or die(mysql_error());
  $r = mysql_fetch_row($q);
  //echo $r[0]."<br>";
  if(strcmp($r[0],$passw) == 0 && !empty($column)) {
    $q = mysql_query("UPDATE user SET logged_in = 1 WHERE ".$column." = '$field'", $con) or die(mysql_error());
    $result = array("logged_in" => "1");
    echo json_encode($result);
  } else {
    $result = array("logged_in" => "0", "error"=>"There was an error logging you in! Please check your password and try again.");
    echo json_encode($result);
  }
?>