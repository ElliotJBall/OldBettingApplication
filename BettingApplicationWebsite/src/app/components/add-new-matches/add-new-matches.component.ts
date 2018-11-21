import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-add-new-matches',
  templateUrl: './add-new-matches.component.html',
  styleUrls: ['./add-new-matches.component.css']
})
export class AddNewMatchesComponent implements OnInit {

  private csvData: string[];

  constructor() { }

  ngOnInit() { }

  public changeListener(files: FileList) {
    if(files && files.length > 0) {
      let file : File = files.item(0); 
        let reader: FileReader = new FileReader();

        reader.readAsText(file);
        reader.onload = () => {
           this.csvData = reader.result.split("\r");
           //this.csvData.splice(0, 1); // Remove the headers from the CSV file

           document.getElementById('matchesToUploadDisplay').style.display = "block";
        }
     }
  }

  private addNewMatchesToDb() {
    console.log(JSON.stringify(this.csvData[1]));
  }
}
