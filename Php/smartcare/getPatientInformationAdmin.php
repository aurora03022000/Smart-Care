<?php
if(isset($_POST['patient_id'])){
    require_once "conn.php";
    require_once "validate.php";
        
    $patientID = validate($_POST['patient_id']);
        
    $sql2 = "SELECT * FROM patient WHERE patient_id = '$patientID'";
    $result2 = $conn->query($sql2);

    if($result2){
        while($row1 = mysqli_fetch_assoc($result2)){
            $sql3 = "SELECT * FROM user WHERE user_id = '$row1[encoder]'";
            $result3 = $conn->query($sql3);

            if($result3){
                while($row2 = mysqli_fetch_assoc($result3)){
                    echo $row1['patient_id']."`".$row1['patient_name'].'`'.$row1['gender'].'`'.$row1['birthdate'].'`'.$row1['address'].'`'.$row1['number'].'`'.$row1['disease'].'`'.$row1['deviceID'].'`'.$row1['room'].'`'.$row2['staff_name'].'`'.$row1['date_admit'].'`'.$row1['date_dismiss'].'`';
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
}
?>