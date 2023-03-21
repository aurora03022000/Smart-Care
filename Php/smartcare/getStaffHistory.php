<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM user ORDER BY staff_name ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['user_id'].'`'.$row['username'].'`'.$row['staff_name'].'`'.$row['gender'].'`'.$row['address'].'`'.$row['date_registered'].'`'.$row['date_deleted'].'`';
        } 
     }
 
     else{
         echo 'failure';
     }
    
?>