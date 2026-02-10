import HeroSection from "../components/landing/HeroSection";
import FeaturesSection from "../components/landing/FeaturesSection";
import PricingSection from "../components/landing/PricingSection";
import { useClerk, useUser } from "@clerk/clerk-react";
import { useNavigate } from "react-router-dom";
import { useEffect } from "react";

const Landing = () => {

    const {openSignUp, openSignIn} = useClerk();
    const {isSignedIn} = useUser();
    const navigate = useNavigate();

  const onGetStarted = () => {
    openSignUp();
  };

  const onSignIn = () => {
    openSignIn();
  };

  useEffect(()=>{
    if(isSignedIn){
        navigate("/dashboard");
    }
  },[isSignedIn,navigate])
  return (
    <div>
      <HeroSection
        onGetStarted={onGetStarted}
        onSignIn={onSignIn}
      />
      <FeaturesSection />
      <PricingSection openSignUp={openSignUp} />
    </div>
  );
};

export default Landing;
