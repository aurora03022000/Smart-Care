<?php
if(isset($_POST['selectedValue']) && isset($_POST['searchValue'])){
    require_once "conn.php";
    require_once "validate.php";
    
    $selectedValue = validate($_POST['selectedValue']);
    $searchValue = validate($_POST['searchValue']);

    
   if($selectedValue == "Patient Name"){
        $sql1 = "SELECT * FROM patient WHERE patient_name LIKE '%$searchValue%'";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Date Encoded"){
        $activityDate = new DateTime($searchValue);
        $activityDateFinal = $activityDate->format('F d, Y');

        $sql1 = "SELECT * FROM patient_logs WHERE date = '$activityDateFinal' ORDER BY `date` ASC";
        $result1 = $conn->query($sql1); 
    }

    else if($selectedValue == "Time Encoded"){
        $firstTwoString = substr($searchValue, 0, 2);//kwaon ang first wo value sang input
        $getAMorPM = substr($searchValue, 6);//tapos kwaon naman ang last two letters sang input

        $searchValue = $firstTwoString;

        $sql1 = "SELECT * FROM patient_logs WHERE time LIKE '%$searchValue%' ORDER BY `date` ASC";
        $result1 = $conn->query($sql1); 
    }

    if($result1){

        if($selectedValue == "Date Encoded"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM patient WHERE patient_id = '$row[patient_id]'";
                $result2 = $conn->query($sql2); 

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row['id'].'`'.$row1['patient_name'].'`'.$row['date'].'`'.$row['time'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else if($selectedValue == "Patient Name"){
            while($row = mysqli_fetch_assoc($result1)){
                $sql2 = "SELECT * FROM patient_logs WHERE patient_id = '$row[patient_id]' ORDER BY `date` ASC";
                $result2 = $conn->query($sql2); 

                if($result2){
                    while($row1 = mysqli_fetch_assoc($result2)){
                        echo $row1['id'].'`'.$row['patient_name'].'`'.$row1['date'].'`'.$row1['time'].'`';
                    }
                }

                else{
                    echo 'failure';
                }
            }
        }

        else{
            while($row2 = mysqli_fetch_assoc($result1)){
                $timeStamp = $row2['time'];
                $getAMorPM1 = substr($timeStamp, 6); 

                if($getAMorPM == $getAMorPM1){
                    $sql2 = "SELECT * FROM patient WHERE patient_id = '$row2[patient_id]'";
                    $result2 = $conn->query($sql2); 

                    if($result2){
                        while($row1 = mysqli_fetch_assoc($result2)){
                            echo $row2['id'].'`'.$row1['patient_name'].'`'.$row2['date'].'`'.$row2['time'].'`';
                        }
                    }

                    else{
                        echo 'failure';
                    }
                }
            }
        }
    }

    else{
        echo 'failure';
    }
    
}
?>