import java.util.*;

public class Ejercicio3 {
  String objetivo;
  List<String> prohibidos = new ArrayList<>();
  List<String[]> validados = new ArrayList<>();
  int totalOperaciones = 0;

  public static void main(String[] args) {
    Ejercicio3 ejercicio3 = new Ejercicio3();
    List<String> casos = ejercicio3.obtenerCasos();

    casos.forEach(caso -> {
      List<String> valores = Arrays.asList(caso.split(" "));
      ejercicio3.objetivo = valores.get(0);
      ejercicio3.prohibidos = valores.subList(1, valores.size());
      ejercicio3.calcularOperaciones(1, 0, 0);
    });
  }

  public int calcularOperaciones(int numeroActual, int vecesDelNumero, int suma){

    return 0;
  }

  public List<String> obtenerCasos(){
    Scanner sc = new Scanner(System.in);
    List<String> casos = new ArrayList<>();

    System.out.println("Escribe 0 cuando quieras dejar de insertar casos");
    while(true){
      System.out.print("Nuevo caso: ");
      String caso = sc.nextLine().trim();
      if(caso.matches("0")) {
        break;
      }

      List<String> datos = Arrays.asList(caso.split(" "));
      boolean correcto = true;
      datos.forEach(dato -> {
        if(!dato.matches("[0-9]")){
          System.out.println("Caso inválido. Introduce números positivos");
        }
      });
      if(!correcto){
        continue;
      }

      casos.add(caso);
    }
    sc.close();
    return casos;
  }
}
