import {
  Upload,
  Lock,
  Share2,
  Coins,
  FolderOpen,
  Receipt
} from "lucide-react";

export const features = [
  {
    title: "Easy File Upload",
    description: "Quickly upload your files with our intuitive drag-and-drop interface.",
    icon: Upload,
  },
  {
    title: "Secure Storage",
    description: "Your files are encrypted and stored securely in our cloud infrastructure.",
    icon: Lock,
  },
  {
    title: "Simple Sharing",
    description: "Share files with anyone using secure links that you control.",
    icon: Share2,
  },
  {
    title: "Flexible Credits",
    description: "Pay only for what you use with our credit-based system.",
    icon: Coins,
  },
  {
    title: "File Management",
    description: "Organize, preview, and manage your files from any device.",
    icon: FolderOpen,
  },
  {
    title: "Transaction History",
    description: "Keep track of all your credit purchases and usage.",
    icon: Receipt,
  },
];

export const pricingPlans = [
  {
    name: "Free",
    description: "Perfect for getting started",
    price: "$0",
    features: [
      "5 file uploads",
      "Basic file sharing",
      "7-day file retention",
      "Email support",
    ],
    cta: "Get Started",
    popular: false,
  },
  {
    name: "Premium",
    description: "For individuals with larger needs",
    price: "$500",
    features: [
      "500 file uploads",
      "Advanced file sharing",
      "30-day file retention",
      "Priority email support",
      "File analytics",
    ],
    cta: "Go Premium",
    popular: true,
  },
  {
    name: "Ultimate",
    description: "For teams and businesses",
    price: "$2500",
    features: [
      "5000 file uploads",
      "Team sharing capabilities",
      "Unlimited file retention",
      "24/7 priority support",
      "Advanced analytics",
      "API access",
    ],
    cta: "Go Ultimate",
    popular: false,
  },
];
