<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM patient_logs ORDER BY `date` ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE patient_id='$row[patient_id]'";
            $result1 = mysqli_query($conn, $sql1);

            if($result1){
                while($row1=mysqli_fetch_assoc($result1)){
                    echo $row1['patient_id'].'`'.$row1['patient_name'].'`'.$row['date'].'`'.$row['time'].'`';
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