<?php

    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_patient_monitored";
    $result = $conn->query($sql);
    
    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE patient_id='$row[patient_id]' and isExist=1";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row=mysqli_fetch_assoc($result1)){
                    echo $row['patient_name'];
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