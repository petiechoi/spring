function function1(dataUrl, filename){
//    alert("zz");
//    let zzz =dataUrl + '/' + filename;
//    console.log(zzz);


  var element = document.createElement('a');
  element.setAttribute('href',dataUrl);
  element.setAttribute('download', filename);
  document.body.appendChild(element);
  element.click();
  //document.body.removeChild(element);
      const blob = new Blob([this.content], {type: 'text/plain'})
      const url = window.URL.createObjectURL(blob)
      const a = document.createElement("a")
      a.href = url
      a.download = `${this.$store.state.nickname}_${this.title}.md`
      a.click()
      a.remove()
      window.URL.revokeObjectURL(url);

}