<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM history_log WHERE isExist=1 ORDER BY history_id DESC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $getStaffName = "SELECT * FROM user WHERE user_id='$row[user]' ";
            $getStaffNameResult = mysqli_query($conn, $getStaffName);

            if($getStaffNameResult){
                while($row1 = mysqli_fetch_assoc($getStaffNameResult)){
                    echo $row['history_id'].'`'.$row1['staff_name'].'`'.$row['dateStamp'].'`'.$row['timeStamp'].'`'.$row['activity'].'`';
                }
            }

            else{
                echo 'failure';      
            }
        } 
     }
 
     else{
         echo 'failure';
     }
    
?>