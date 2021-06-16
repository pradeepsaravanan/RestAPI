$(document).ready(function () {
  var response;
  $.get("http://localhost:8082/REST-API/product", (data, error) => {
    response = JSON.parse(data);
    for (var i = 0; i < response.length; i++) {
      $("#items").append(
        "<div class='productName' pdtId=" +
          i +
          ">" +
          response[i].name +
          "</div>"
      );
    }
    onPageLoad();
  });

  let onPageLoad = () => {
    $(".productName").click((event) => {
      let pdtId = $(event.target).attr("pdtId");
      console.log(response[pdtId].name);
      $("#details").html("<div id='details1' class='productName'><div>");
      $("#details1").append("<div>Name= " + response[pdtId].name + "</div>");
      $("#details1").append("<div>price= " + response[pdtId].price + "</div>");
      $("#details1").append("<div>stock= " + response[pdtId].stock + "</div>");
    });
  };
});
