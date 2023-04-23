import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.*;

public class ClientMatrice {

    public static void afficherMatrice(double[][] matrice) {
        for (int i = 0; i < matrice.length; i++) {
            for (int j = 0; j < matrice[i].length; j++) {
                System.out.print(matrice[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        try {
            // Se connecter au registre RMI

            Registry registry = LocateRegistry.getRegistry("localhost", 1099);

            // Obtenir une référence vers l'objet distant ServeurMatrice
            ServeurMatrice serveurMatrice = (ServeurMatrice) registry.lookup("serveurMatrice");

            // Demander à l'utilisateur de s'identifier auprès du serveur
            Scanner scanner = new Scanner(System.in);
            System.out.print("register  : ");
            String nomUtilisateur = scanner.nextLine();

            // Appeler la méthode distante d'authentification
            serveurMatrice.register(nomUtilisateur, serveurMatrice);

            // Afficher les opérations disponibles
            HashMap<String, String> products = serveurMatrice.getAllData();
            System.out.println("----------------------------------------");

            System.out.println("The List Of Our Products : ");

            for(String key : products.keySet()) {
                System.out.println("ID : "+ key + ":-------> product : " + products.get(key));
            }
            System.out.println("----------------------------------------");

            System.out.println("do u want to see details about product or add new one :  ");
            System.out.println("(1)------------> see details");
            System.out.println("(ADD)-------------> add new product ");
            String choice0 = scanner.nextLine();

            switch (choice0) {
                case "1":
                System.out.println("enter the id of product : ");
                String e = scanner.nextLine();

                Details product = serveurMatrice.InfoAboutProduct(e);
                System.out.println("----------------------------------------");
    
                System.out.println("name of product :  "+product.name);
                System.out.println("description of product :  "+product.description);
                System.out.println("price of product :  "+product.price);
                System.out.println("quantity of product :  "+product.quantity);
    
                System.out.println("----------------------------------------");
    
                System.out.println("Do u want to Delete or Update : ");
                System.out.println("(1)------------> Delete");
                System.out.println("(2)-------------> Update the quantity of product");
                System.out.println("(3)-------------> Nothing");

                String choice1 = scanner.nextLine();
    
                switch (choice1) {
                    case "1":
                    String key = product.id;
                    serveurMatrice.Delete_product(key);

                    serveurMatrice.sendData(nomUtilisateur+ " Delete  that product : " + product.name);

                    break;
                    case "2":
                    String key1 = product.id;
                    System.out.println("The new quantity : ");
                    int quant = scanner.nextInt();
                    serveurMatrice.Update_product_quantity(key1,quant);
    
                    Details product_update= serveurMatrice.InfoAboutProduct(key1);
                    System.out.println("----------------------------------------");
        
                    System.out.println("name of product :  "+product_update.name);
                    System.out.println("description of product :  "+product_update.description);
                    System.out.println("price of product :  "+product_update.price);
                    System.out.println("New quantity of product :  "+product_update.quantity);
        
                    System.out.println("----------------------------------------");
        
                     serveurMatrice.sendData(nomUtilisateur+ " Update that product " + product.name);

                    break;
                    
                    case "3":
                    System.out.println("Good bye");

                    break;
    
                default:
                    System.out.println("choice doesn't existe !!!!!!");
                    break;
            }
            break;

            case "ADD" :
             
            System.out.println("new name of product :  ");
            String a = scanner.nextLine();

            System.out.println("new name of categorie :  ");
            String a4 = scanner.nextLine();


            boolean check = serveurMatrice.check_catgorie_exist(a4);

            if(check==true){
                System.out.println("new description of product :  ");
                String a1 = scanner.nextLine();
    
                System.out.println("new price of product :  ");
                String a2 = scanner.nextLine();
    
                System.out.println("new quantity of product :  ");
                int a3 = scanner.nextInt();
     
                serveurMatrice.add_product(a,a4,a1,a2,a3);


                HashMap<String, String> product_new = serveurMatrice.getAllData();

                serveurMatrice.sendData(nomUtilisateur+ " add that product " + a4);

    


            }
            else{
                System.out.println("change the name of product to  accept it ");

            }

        
            break;

            default:
                    System.out.println("Ur order doesn't existe !!!!");
            break;




            }

       


           

        
            
    
    } 
    catch (Exception e) {
        System.err.println("Erreur : " + e.getMessage());
        e.printStackTrace();
    }

    }
}
