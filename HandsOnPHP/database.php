<?php
  $con = mysql_connect('ec2-184-73-92-152.compute-1.amazonaws.com','root','bitnami');
  if(!$con) {
    die("ERROR: Cannot connect to database!");
  }
?>