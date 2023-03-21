<?php
    require_once "conn.php";
    require_once "validate.php";
    
    $sql = "SELECT * FROM current_patient_monitored WHERE id='1'";
    $result = $conn->query($sql);
    
    if($result){
        while($row=mysqli_fetch_assoc($result)){
            $sql1 = "SELECT * FROM patient WHERE patient_id='$row[patient_id]' and isExist=1";
            $result1 = $conn->query($sql1);

            if($result1){
                while($row1=mysqli_fetch_assoc($result1)){
                    echo $row1['patient_name'];
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