<?php

$connection = null;

try{
    // config
    $host = "localhost";
    $name = "root";
    $password = "";
    $dbname = "keephealthy";

    // Connect
    $database = "mysql:dbname=$dbname;host=$host";
    $connection = new PDO($database,$name,$password);
    $connection->setAttribute(PDO::ATTR_ERRMODE, PDO::ERRMODE_EXCEPTION);

    // if($connection){
    //     echo "koneksi bersih";
    // } else {
    //     echo "gagal";
    // }

} catch(PDOException $e){
    echo "error !" . $e->getMessage();
    die;
}
?>