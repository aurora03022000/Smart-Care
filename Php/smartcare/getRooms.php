<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM room ORDER BY room_number ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE isExist=1 AND room = '$row[room_number]'";
            $result1 = mysqli_query($conn, $sql1);
            $result1Count = mysqli_num_rows($result1);

            if($result1Count > 0){
                while($row1 = mysqli_fetch_assoc($result1)){
                    echo $row['room_number'].'`'.$row['room_status'].'`'.$row1['patient_name'].'`';
                }
            }

            else if($result1Count == 0){
                echo $row['room_number'].'`'.$row['room_status'].'`'."-".'`';
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