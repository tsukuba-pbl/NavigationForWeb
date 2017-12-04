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
  createdAt: Date,
  updatedAt: Date,
}

type LocationType = {
  id: number,
  name: string,
  detail: string,
  eventId: string,
}

type ResponseData = {
  status: number,
  message: string,
  data: any
}

type BeaconType = {
  eventid: string,
  minorid: number;
}