<?php

include 'connection.php';

if($_POST){

    // post data
    $name = filter_input(INPUT_POST, 'name', FILTER_SANITIZE_STRING);
    $email = filter_input(INPUT_POST, 'email');
    $alamat = filter_input(INPUT_POST, 'alamat', FILTER_SANITIZE_STRING);
    $nomor = filter_input(INPUT_POST, 'nomor', FILTER_SANITIZE_STRING);
    $password = filter_input(INPUT_POST, 'password', FILTER_SANITIZE_STRING);

    $response = [];
    // cek username di dalam database
    $userQuery = $connection->prepare("SELECT * FROM users where name = ?");
    $userQuery ->EXECUTE(array($name));

    // cek username apakah ada atau tidak 
    if($userQuery->rowCount() != 0){
        $response['status']= false;
        $response['message']='akun sudah digunakan';

    } else {
        $insertAccount = 'INSERT INTO users (name,email,alamat,nomor,password) values (:name, :email, :alamat, :nomor , :password)';
        $statement = $connection->prepare($insertAccount);

        try{
            $statement->execute([

                ':name' => $name,
                ':email' => $email,
                ':alamat' => $alamat,
                ':nomor' => $nomor,
                ':password' => md5($password)
            ]);
            
            // response
            $response['status'] = true;
            $response['message'] = 'akun berhasil didaftarkan';
            $response['data'] = [
                'name' => $name,
                'email' => $email,
                'alamat' => $alamat,
                'nomor' => $nomor
            ];
        } catch (Exception $e){
            die($e->getMessage());
        }
        
    }

    $json = json_encode($response, JSON_PRETTY_PRINT);
    echo $json;
}