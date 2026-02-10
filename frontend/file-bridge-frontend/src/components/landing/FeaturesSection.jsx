import { Card, CardContent, Typography } from "@mui/material";
import { features } from "../../data/landingData";

const FeaturesSection = () => {
  return (
    <div className="py-20 px-6 bg-gray-50">
      <div className="text-center mb-12">
        <Typography variant="h4" className="font-semibold mb-2">
          Everything you need for file sharing
        </Typography>
        <Typography className="text-gray-600">
          CloudShare provides all the tools you need to manage your digital content
        </Typography>
      </div>

      <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-6 max-w-6xl mx-auto">
        {features.map((feature, idx) => {
          const Icon = feature.icon;
          return (
            <Card key={idx} className="shadow-sm">
              <CardContent className="flex flex-col gap-3">
                <Icon className="w-8 h-8 text-blue-600" />
                <Typography variant="h6">
                  {feature.title}
                </Typography>
                <Typography className="text-gray-600 text-sm">
                  {feature.description}
                </Typography>
              </CardContent>
            </Card>
          );
        })}
      </div>
    </div>
  );
};

export default FeaturesSection;
