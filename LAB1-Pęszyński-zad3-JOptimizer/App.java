package watminwd;

import com.joptimizer.exception.JOptimizerException;
import com.joptimizer.functions.ConvexMultivariateRealFunction;
import com.joptimizer.functions.LinearMultivariateRealFunction;
import com.joptimizer.optimizers.JOptimizer;
import com.joptimizer.optimizers.OptimizationRequest;

public class App 
{
    public static void main( String[] args )
    {
        double x = 0.3;
        double y = 0.2;

        //   ---------   goal function    -----------
        LinearMultivariateRealFunction goalFunction = new LinearMultivariateRealFunction(new double[] {-x, -y}, 0);

        //  ---------   dual form due to 2 kinds of cut    -----------
        ConvexMultivariateRealFunction[] inEqualities = new ConvexMultivariateRealFunction[4];

        // 7x >= 2100
        inEqualities[0] = new LinearMultivariateRealFunction(new double[]{7.0, 0.0}, -2100.0);
        // 2y >= 1200
        inEqualities[1] = new LinearMultivariateRealFunction(new double[]{0.0, 2.0}, -1200.0);
        // x >= 0
        inEqualities[2] = new LinearMultivariateRealFunction(new double[]{-1.0, 0.0}, 0);
        // y >= 0
        inEqualities[3] = new LinearMultivariateRealFunction(new double[]{0.0, -1.0}, 0);

        //  ---------   configuration    -----------
        OptimizationRequest or = new OptimizationRequest();
        or.setF0(goalFunction);
        or.setFi(inEqualities);
        or.setToleranceFeas(JOptimizer.DEFAULT_FEASIBILITY_TOLERANCE / 10);
        or.setTolerance(JOptimizer.DEFAULT_TOLERANCE / 10);

        //  ---------   optimization    -----------
        JOptimizer opt = new JOptimizer();
        opt.setOptimizationRequest(or);
        try {
            opt.optimize();
        }
        catch (Exception ex) {
            ex.printStackTrace();
            return;
        }

        //  ---------   solution    -----------
        double[] sol = opt.getOptimizationResponse().getSolution();

        //  ---------   printing out solution    -----------
        System.out.println("Length: " + sol.length);
        for (int i = 0; i < sol.length; i++) {
            System.out.println("Answer " + (i+1)+ ": " + (sol[i]));
        }
        double wastes = x*sol[0]+y*sol[1];
        System.out.println(wastes);
        System.out.println("\nAfter round\n");

        for (int i = 0; i < sol.length; i++) {
            System.out.println("Answer " + (i+1)+ ": " + Math.round(sol[i]));
        }

        System.out.println(Math.round(wastes));
    }
}
