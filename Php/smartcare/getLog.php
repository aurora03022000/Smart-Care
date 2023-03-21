<?php
if(isset($_POST['patientID'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $patientID = validate($_POST['patientID']);
    
    $sql = "SELECT * FROM patient_logs WHERE patient_id='$patientID' ORDER BY `date` ASC";
    $result = mysqli_query($conn, $sql);

    if($result){
        while($row=mysqli_fetch_assoc($result)){
            echo $row['date']." - ".$row['time']."`";
        }
    }

    else{
        echo 'failure';
    }

}
?>