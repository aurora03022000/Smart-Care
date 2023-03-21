<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM deviceid WHERE device_status ='Available' ORDER BY device_id ASC";
    $result = mysqli_query($conn, $sql);
    
    if($result){
       while($row=mysqli_fetch_assoc($result)){
            echo $row['device_id']."`";
        } 
    }

    else{
        echo 'failure';
    }
    
?>