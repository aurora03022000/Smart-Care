<?php
if(isset($_POST['hospital_name']) ){
    require_once "conn.php";
    require_once "validate.php";
    
    $hospital_name = validate($_POST['hospital_name']);

    $sql = "SELECT * FROM hospital_list WHERE hospital_name='$hospital_name'";
    $result = mysqli_query($conn, $sql);
    $countRow = mysqli_num_rows($result);

    if($countRow > 0){
        echo 'duplicate';
    }

    else if($countRow == 0){
       $insertHospitalName = "INSERT INTO hospital_list(`hospital_name`) VALUES('$hospital_name')";
       $insertHospitalNameResult = mysqli_query($conn, $insertHospitalName);

       if($insertHospitalNameResult){
        echo 'success';
       }

       else{
        echo 'failure';
       }
    }
 
     else{
         echo 'failure';
     }

}
?>