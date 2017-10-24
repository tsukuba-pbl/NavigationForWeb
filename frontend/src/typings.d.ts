/* SystemJS module definition */
declare var module: NodeModule;
interface NodeModule {
  id: string;
}

type EventType = {
  id: string,
  name: string,
  description: string,
  location: string,
  startDate: Date,
  endDate: Date,
  userId: string,
}