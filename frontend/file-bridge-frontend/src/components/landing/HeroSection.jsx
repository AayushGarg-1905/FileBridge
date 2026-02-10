import { Button, Typography } from "@mui/material";

const HeroSection = ({ onGetStarted, onSignIn }) => {
    return (
        <div className="flex flex-col items-center text-center py-24 px-4">
            <Typography variant="h3" className="font-bold mb-4">
                Secure File Sharing with{" "}<br/>
                <span className="text-blue-600">
                    FileBridge
                </span>
            </Typography>

            <Typography className="text-gray-600 max-w-xl mb-8">
                Upload, manage, and share files securely with
                flexible credit-based pricing.
            </Typography>


            <div className="flex gap-4 mt-[0.5rem]">
                <Button variant="contained" onClick={onGetStarted}>
                    Get Started
                </Button>
                <Button variant="outlined" onClick={onSignIn}>
                    Sign In
                </Button>
            </div>
        </div>
    );
};

export default HeroSection;
