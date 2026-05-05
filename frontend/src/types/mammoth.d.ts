declare module 'mammoth' {
  interface Message {
    type: string;
    message: string;
  }
  
  interface ExtractResult {
    value: string;
    messages: Message[];
  }
  
  function extractRawText(options: { arrayBuffer: ArrayBuffer }): Promise<ExtractResult>;
  
  export { ExtractResult, Message, extractRawText };
}