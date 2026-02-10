import { Card, CardContent, Button, Typography } from "@mui/material";
import { pricingPlans } from "../../data/landingData";

const PricingSection = ({openSignUp}) => {
  return (
    <div className="py-20 px-6">
      <div className="text-center mb-12">
        <Typography variant="h4" className="font-semibold">
          Pricing Plans
        </Typography>
      </div>

      <div className="grid grid-cols-1 md:grid-cols-3 gap-6 max-w-6xl mx-auto">
        {pricingPlans.map((plan, idx) => (
          <Card
            key={idx}
            className={`border ${
              plan.popular ? "border-blue-600" : "border-gray-200"
            }`}
          >
            <CardContent className="flex flex-col gap-4">
              {plan.popular && (
                <span className="text-xs text-blue-600 font-semibold">
                  Popular
                </span>
              )}

              <Typography variant="h6">{plan.name}</Typography>
              <Typography className="text-gray-600 text-sm">
                {plan.description}
              </Typography>

              <Typography variant="h4" className="font-bold">
                {plan.price}
              </Typography>

              <ul className="text-sm text-gray-600 space-y-1">
                {plan.features.map((f, i) => (
                  <li key={i}>✓ {f}</li>
                ))}
              </ul>

              <Button
                variant={plan.popular ? "contained" : "outlined"}
                className="mt-4"
                fullWidth
                onClick={()=>openSignUp()}
              >
                {plan.cta}
              </Button>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
};

export default PricingSection;
