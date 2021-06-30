<?php 

include 'connection.php';

if($_POST){

    // data
    $name = $_POST['name'] ?? '';
    $password = $_POST['password'] ?? '';

    $response = []; 
    
    // cek username di dalam database
    $userQuery = $connection->prepare("SELECT * FROM users where name = ?");
    $userQuery ->EXECUTE(array($name));
    $query = $userQuery->fetch();

    if($userQuery->rowCount() == 0){
        $response['status'] = false;
        $response['message'] = "Username Tidak Terdaftar";
    } else {
        // ambil password di database

        $passwordDB = $query['password'];

        if(strcmp(md5($password),$passwordDB) == 0){
            $response['status'] = true;
            $response['message'] = "login berhasil";
            $response['data'] = [
                'user_id' => $query['id'],
                'name' => $query['name'],
            ];
        } else {
            $response['status'] = false;
            $response['message'] = "password anda salah";
        }
    }

    // jadikan json
    $json = json_encode($response, JSON_PRETTY_PRINT);
    // PRINT
    echo $json;
}