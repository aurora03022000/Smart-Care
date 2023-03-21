<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_patient_monitored";
    $result = $conn->query($sql);
    
    if($result){
        while($row = mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE patient_id='$row[patient_id]' and isExist=1 ORDER BY patient_name ASC";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row1 = mysqli_fetch_assoc($result1)){
                    echo $row1['patient_name']."`".$row1['gender']."`".$row1['birthdate']."`".$row1['address']."`".$row1['number']."`".$row1['disease']."`".$row1['room']."`".$row1['deviceID']."%";
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