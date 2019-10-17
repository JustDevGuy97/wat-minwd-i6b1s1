using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using Accord.Math;
using Accord.Math.Optimization;

namespace Banach
{
    class Program
    {


        public static void AugmentedLagrangianSolverConstructorTest4()
        {
            

            double x = 0, y = 0;

            var f = new NonlinearObjectiveFunction(

                function: () => 0.3*x+0.6*y,

                gradient: () => new[]
                {
                    0.3,     // df/dx
                    0.6, // df/dy
                         
                }

            );



            var constraints = new List<NonlinearConstraint>();

            constraints.Add(new NonlinearConstraint(f,

                function: () => 7*x+3*y,
                gradient: () => new[] { 7.0 , 3.0},

                shouldBe: ConstraintType.GreaterThanOrEqualTo, value: 2100
            ));

            constraints.Add(new NonlinearConstraint(f,

    function: () => y,
    gradient: () => new[] { 0, 1.0 },

    shouldBe: ConstraintType.GreaterThanOrEqualTo, value: 1200
));

            constraints.Add(new NonlinearConstraint(f,

                function: () => x,
                gradient: () => new[] { 1.0, 0 },

                shouldBe: ConstraintType.GreaterThanOrEqualTo, value: 0
            ));



            var solver = new AugmentedLagrangian(f, constraints);

            solver.Solution[0] = 1;
            solver.Solution[1] = 1;
   

            bool success = solver.Minimize();

            double[] solution = solver.Solution;


            double minValue = solver.Value;
            Console.WriteLine("Solver Value = " + solver.Value);
            Console.WriteLine("Solver Minize = " + solver.Minimize());
            Console.WriteLine("Solver Solution x = " + solver.Solution[0]);
            Console.WriteLine("Solver Solution y = " + solver.Solution[1]);
            Console.ReadKey();


        }

        static void Main(string[] args)
        {

            AugmentedLagrangianSolverConstructorTest4();

        }
    }
}
