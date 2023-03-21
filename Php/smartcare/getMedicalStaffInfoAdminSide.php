<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM user WHERE isExist=1 ORDER BY staff_name ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['user_id'].'`'.$row['staff_name'].'`'.$row['username'].'`'.$row['birthdate'].'`'.$row['gender'].'`'.$row['address'].'`';
        } 
     }
 
     else{
         echo 'failure';
     }
    
?>